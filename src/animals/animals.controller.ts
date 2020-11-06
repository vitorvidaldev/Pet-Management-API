import { Controller, Post, Body, ValidationPipe, UsePipes, Get, Param, Delete, ParseUUIDPipe, UseGuards } from '@nestjs/common';
import { AnimalsService } from './animals.service';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { Animal } from './animal.entity';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';
import { AuthGuard } from '@nestjs/passport';
import { GetUser } from 'src/users/get-user.decorator';
import { User } from 'src/users/user.entity';

@ApiTags('Animais')
@ApiResponse({ status: 401, description: 'Usuário não autorizado' })
@Controller('animals')
@UseGuards(AuthGuard())
export class AnimalsController {
    constructor(private animalsService: AnimalsService) { }

    @ApiOperation({ summary: 'Faz o cadastro de um novo animal no banco de dados.' })
    @ApiResponse({ status: 201, description: 'Retorna o novo animal cadastrado' })
    @Post()
    @UsePipes(ValidationPipe)
    createAnimal(@Body() createAnimalDto: CreateAnimalDto, @GetUser() user: User): Promise<Animal> {
        return this.animalsService.createAnimal(createAnimalDto, user);
    }

    @ApiOperation({ summary: 'Retorna a lista com todos os animais cadastrados pelo usuário com dado id.' })
    @ApiResponse({ status: 200, description: 'Retorna a lista de animais de dado usuário' })
    @Get()
    getAnimals(@GetUser() user: User): Promise<Animal[]> {
        return this.animalsService.getAnimals(user);
    }

    @ApiOperation({ summary: 'Retorna o animal com dado id.' })
    @ApiParam({ name: 'id', description: 'id do animal.' })
    @ApiResponse({ status: 200, description: 'Retorna o animal que possui dado id.' })
    @Get(':id')
    getAnimalById(@Param('id', new ParseUUIDPipe()) id: string, @GetUser() user: User): Promise<Animal> {
        return this.animalsService.getAnimalById(id, user);
    }

    @ApiOperation({ summary: 'Exclui um animal do banco de dados.' })
    @ApiParam({ name: 'id', description: 'id do animal.' })
    @ApiResponse({ status: 200, description: 'O animal com dado id foi excluido.' })
    @Delete(':id')
    deleteAnimal(@Param('id', new ParseUUIDPipe()) id: string, @GetUser() user: User): Promise<void> {
        return this.animalsService.deleteAnimal(id, user);
    }
}
