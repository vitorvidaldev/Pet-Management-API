import { Controller, Body, ValidationPipe, Post, UsePipes, Get, Param, ParseUUIDPipe, Delete } from '@nestjs/common';
import { UsersService } from './users.service';
import { CreateUserDto } from './dto/create-user.dto';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';
import { User } from './user.entity';

@ApiTags('Usuários')
@Controller('users')
export class UsersController {
    constructor(private usersService: UsersService) { }

    @ApiOperation({ summary: 'Retorna todos os usuários cadastrados no banco de dados.' })
    @ApiResponse({ status: 200, description: 'Retorna uma lista com os usuários cadastrados' })
    @Get()
    getUsers(): Promise<User[]> {
        return this.usersService.getUsers();
    }

    @ApiOperation({ summary: 'Retorna o usuário que possui dado id.' })
    @ApiParam({ name: 'id', description: 'id de identificação de um usuário' })
    @ApiResponse({ status: 200, description: 'Retorna o usuário.' })
    @Get(':id')
    getUserById(@Param('id', new ParseUUIDPipe()) id: string): Promise<User> {
        return this.usersService.getUserById(id);
    }

    @ApiOperation({ summary: 'Realiza o cadastro de um novo usuário' })
    @ApiResponse({ status: 201, description: 'Retorna o usuário cadastrado.' })
    @ApiResponse({ status: 400, description: 'O email ou a senha enviados estão incorretos.' })
    @Post()
    @UsePipes(ValidationPipe)
    createUser(@Body() createUserDto: CreateUserDto): Promise<User> {
        return this.usersService.createUser(createUserDto);
    }

    @ApiOperation({ summary: 'Realiza o login do usuário' })
    @ApiResponse({ status: 201, description: 'Retorna o token de acesso do usuário.' })
    @ApiResponse({ status: 401, description: 'O email ou a senha enviados estão incorretos.' })
    @Post('login')
    login(@Body(ValidationPipe) createUserDto: CreateUserDto): Promise<{ accessToken: string }> {
        return this.usersService.login(createUserDto);
    }

    @ApiOperation({ summary: 'Exclusão de um usuário cadastrado.' })
    @ApiParam({ name: 'id', description: 'id de identificação de um usuário' })
    @Delete(':id')
    deleteUser(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
        return this.usersService.deleteUser(id);
    }
}
