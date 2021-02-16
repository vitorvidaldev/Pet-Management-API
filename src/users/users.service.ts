import {
  ConflictException,
  Injectable,
  NotFoundException,
  UnauthorizedException,
} from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { CreateUserDto } from "./dto/create-user.dto";
import { User } from "./user.entity";
import { Repository } from "typeorm";
import * as bcrypt from "bcrypt";
import { JwtService } from "@nestjs/jwt";
import { JwtPayload } from "./jwt-payload.interface";

@Injectable()
export class UsersService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>,
    private jwtService: JwtService
  ) {}

  async getUsers(): Promise<User[]> {
    return await this.userRepository.createQueryBuilder("user").getMany();
  }

  async getUserById(id: string): Promise<User> {
    const found = await this.userRepository.findOne(id);
    if (!found) {
      throw new NotFoundException(
        "Usuário com id " + id + " não foi encontrado."
      );
    }
    return found;
  }

  async createUser(createUserDto: CreateUserDto): Promise<User> {
    const { email, password } = createUserDto;

    const user = new User();
    user.email = email;
    user.signature = await bcrypt.genSalt();
    user.password = await bcrypt.hash(password, user.signature);
    let result;

    try {
      result = await this.userRepository.create(user).save();
    } catch (error) {
      // console.log(error);
      throw new ConflictException(
        `Já existe o cadastro de um usário com o email ${email}`
      );
    }
    return result;
  }

  async login(
    loginUserDto: CreateUserDto
  ): Promise<{ accessToken: string; user: User }> {
    const user = await this.validateUserPassword(loginUserDto);

    if (!user.email) {
      throw new UnauthorizedException(
        "O email ou senha fornecidos estão incorretos"
      );
    }

    const payload: JwtPayload = { email: user.email };
    const accessToken = this.jwtService.sign(payload);

    return { accessToken, user };
  }

  async validateUserPassword(userCredentialsDto: CreateUserDto): Promise<User> {
    const { email, password } = userCredentialsDto;
    const user = await this.userRepository.findOne({ email });

    if (user && (await user.validatePassword(password))) {
      return user;
    } else {
      return null;
    }
  }

  async deleteUser(id: string): Promise<void> {
    await this.userRepository.delete(id);
  }
}
