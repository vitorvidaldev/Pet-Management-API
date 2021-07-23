import {
  Controller,
  Body,
  ValidationPipe,
  Post,
  UsePipes,
  Get,
  Param,
  ParseUUIDPipe,
  Delete,
} from '@nestjs/common';
import { UserService } from './user.service';
import { CreateUserDto } from './dto/create-user.dto';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';
import { User } from './user.entity';

@ApiTags('User')
@Controller('user')
export class UserController {
  constructor(private usersService: UserService) {}

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({
    summary: 'Returns all users registered in the database.',
  })
  @ApiResponse({
    status: 200,
    description: 'Returns all users registered in the database',
  })
  @Get()
  findAll(): Promise<User[]> {
    return this.usersService.findAll();
  }

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: 'Returns an user object' })
  @ApiParam({ name: 'id', description: 'User id' })
  @ApiResponse({
    status: 200,
    description: 'Returns the user object who has the given id.',
  })
  @Get(':id')
  findById(@Param('id', new ParseUUIDPipe()) id: string): Promise<User> {
    return this.usersService.findById(id);
  }

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: 'Register a new user' })
  @ApiResponse({ status: 201, description: 'The new user was created.' })
  @ApiResponse({
    status: 400,
    description: 'The email or password sent was invalid.',
  })
  @Post()
  @UsePipes(ValidationPipe)
  save(
    @Body(ValidationPipe) createUserDto: CreateUserDto,
  ): Promise<Partial<User>> {
    return this.usersService.save(createUserDto);
  }

  // TODO: Change return object. It currently returns password and signature.
  @ApiOperation({ summary: 'User login' })
  @ApiResponse({
    status: 201,
    description: "Returns the user's access token.",
  })
  @ApiResponse({
    status: 401,
    description: 'The email or password sent is incorrect.',
  })
  @Post('login')
  login(@Body(ValidationPipe) createUserDto: CreateUserDto): Promise<string> {
    return this.usersService.login(createUserDto);
  }

  @ApiOperation({ summary: 'Deletes a registered user.' })
  @ApiParam({ name: 'id', description: 'User id' })
  @Delete(':id')
  deleteById(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
    return this.usersService.deleteById(id);
  }
}
