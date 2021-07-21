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
import { UserService } from "./user.service";
import { CreateUserDto } from "./dto/create-user.dto";
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from "@nestjs/swagger";
import { User } from "./user.entity";

@ApiTags("Users")
@Controller("users")
export class UserController {
  constructor(private usersService: UserService) {}

  // TODO: Change return object. It currently returns password and signature.
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

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: "Returns an user object" })
  @ApiParam({ name: "id", description: "User id" })
  @ApiResponse({
    status: 200,
    description: "Returns the user object who has the given id."
  })
  @Get(":id")
  getUserById(@Param("id", new ParseUUIDPipe()) id: string): Promise<User> {
    return this.usersService.getUserById(id);
  }

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: "Register a new user" })
  @ApiResponse({ status: 201, description: "The new user was created." })
  @ApiResponse({
    status: 400,
    description: "The email or password sent was invalid."
  })
  @Post()
  @UsePipes(ValidationPipe)
  createUser(
    @Body(ValidationPipe) createUserDto: CreateUserDto
  ): Promise<Partial<User>> {
    return this.usersService.createUser(createUserDto);
  }

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: "User login" })
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

  @ApiOperation({ summary: "Deletes a registered user." })
  @ApiParam({ name: "id", description: "User id" })
  @Delete(":id")
  deleteUser(@Param("id", new ParseUUIDPipe()) id: string): Promise<void> {
    return this.usersService.deleteUser(id);
  }
}
