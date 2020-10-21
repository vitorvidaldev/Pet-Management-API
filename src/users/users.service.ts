import { Injectable, NotFoundException, UnauthorizedException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateUserDto } from './dto/create-user.dto';
import { User } from './user.entity';
import { Repository } from 'typeorm';
import * as bcrypt from 'bcrypt';

@Injectable()
export class UsersService {

    constructor(
        @InjectRepository(User)
        private userRepository: Repository<User>,
    ) { }

    async getUsers(): Promise<User[]> {
        return await this.userRepository.createQueryBuilder('user').getMany();
    }

    async getUserById(id: string): Promise<User> {
        const found = await this.userRepository.findOne(id);
        if (!found) {
            throw new NotFoundException('Usuário com id ' + id + ' não foi encontrado.');
        }
        return found;
    }

    async createUser(createUserDto: CreateUserDto): Promise<User> {
        const { email, password } = createUserDto;

        const user = new User();
        user.email = email;
        user.signature = await bcrypt.genSalt();
        user.password = await bcrypt.hash(password, user.signature);

        return await this.userRepository.create(user).save();
    }

    async login(loginUserDto: CreateUserDto): Promise<User> {
        const login = await this.validateUserPassword(loginUserDto);

        if (!login) {
            throw new UnauthorizedException('O email ou senha fornecidos estão incorretos');
        }

        const user = await this.userRepository.createQueryBuilder('user')
            .andWhere('user.email = :email', {
                email: loginUserDto.email,
            }).getOne();

        return user;
    }

    async validateUserPassword(userCredentialsDto: CreateUserDto) {
        const { email, password } = userCredentialsDto;
        const user = await this.userRepository.findOne({ email });

        if (user && await user.validatePassword(password)) {
            return user.email;
        } else {
            return null;
        }
    }

    async deleteUser(id: string): Promise<void> {
        await this.userRepository.delete(id);
    }
}
