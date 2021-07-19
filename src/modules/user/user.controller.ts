import {
  Controller,
  Body,
  ValidationPipe,
  Post,
  UsePipes,
  Get,
  Param,
  ParseUUIDPipe,
  Delete
} from "@nestjs/common";
import { UsersService } from "./user.service";
import { CreateUserDto } from "./dto/create-user.dto";
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from "@nestjs/swagger";
import { User } from "./user.entity";

@ApiTags("Users")
@Controller("users")
export class UsersController {
  constructor(private usersService: UsersService) {}

  @ApiOperation({
    summary: "Returns all users registered in the database."
  })
  @ApiResponse({
    status: 200,
    description: "Returns all users registered in the database"
  })
  @Get()
  getUsers(): Promise<User[]> {
    return this.usersService.getUsers();
  }

  @ApiOperation({ summary: "Returns the user who has the given id." })
  @ApiParam({ name: "id", description: "User id" })
  @ApiResponse({
    status: 200,
    description: "Returns the user who has the given id."
  })
  @Get(":id")
  getUserById(@Param("id", new ParseUUIDPipe()) id: string): Promise<User> {
    return this.usersService.getUserById(id);
  }

  @ApiOperation({ summary: "Register a new user" })
  @ApiResponse({ status: 201, description: "Register a new user" })
  @ApiResponse({
    status: 400,
    description: "The email or password sent is incorrect."
  })
  @Post()
  @UsePipes(ValidationPipe)
  createUser(
    @Body(ValidationPipe) createUserDto: CreateUserDto
  ): Promise<Partial<User>> {
    return this.usersService.createUser(createUserDto);
  }

  @ApiOperation({ summary: "Log in the user" })
  @ApiResponse({
    status: 201,
    description: "Returns the user's access token."
  })
  @ApiResponse({
    status: 401,
    description: "The email or password sent is incorrect."
  })
  @Post("login")
  login(
    @Body(ValidationPipe) createUserDto: CreateUserDto
  ): Promise<{ accessToken: string }> {
    return this.usersService.login(createUserDto);
  }

  @ApiOperation({ summary: "Deleting a registered user." })
  @ApiParam({ name: "id", description: "User id" })
  @Delete(":id")
  deleteUser(@Param("id", new ParseUUIDPipe()) id: string): Promise<void> {
    return this.usersService.deleteUser(id);
  }
}
