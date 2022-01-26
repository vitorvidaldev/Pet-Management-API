import {
  ConflictException,
  Injectable,
  NotFoundException,
  PreconditionFailedException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateUserDto } from './dto/create-user.dto';
import { User } from './user.entity';
import { Repository } from 'typeorm';
import * as bcrypt from 'bcrypt';
import { JwtService } from '@nestjs/jwt';
import { JwtPayload } from './jwt-payload.interface';
import { UserDTO } from './dto/user.dto';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>,
    private jwtService: JwtService,
  ) {}

  async save(userDto: CreateUserDto): Promise<Partial<UserDTO>> {
    const { email, password } = userDto;

    const user = new User();
    user.email = email;
    user.signature = await bcrypt.genSalt();
    user.password = await bcrypt.hash(password, user.signature);

    let result: User;
    try {
      result = await this.userRepository.create(user).save();
    } catch (error) {
      throw new ConflictException(
        `A user is already registered with the email ${email}`,
      );
    }
    return result.toUserDTO();
  }

  async login(userDto: CreateUserDto): Promise<string> {
    const user = await this.validateUserPassword(userDto);

    if (!user.email) {
      throw new UnauthorizedException(
        'The email or password provided is incorrect',
      );
    }

    const payload: JwtPayload = { email: user.email };
    const accessToken = this.jwtService.sign(payload);

    return accessToken;
  }

  async validateUserPassword(userCredentialsDto: CreateUserDto): Promise<User> {
    const { email, password } = userCredentialsDto;
    const user = await this.userRepository.findOne({ email });

    if (user && (await user.validatePassword(password))) {
      return user;
    }

    throw new PreconditionFailedException();
  }

  async deleteById(id: string): Promise<void> {
    await this.userRepository.delete(id);
  }
}
