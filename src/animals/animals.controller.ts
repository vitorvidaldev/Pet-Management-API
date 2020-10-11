import { Controller, Post, Body, ValidationPipe, UsePipes, Get, Param, Delete, ParseUUIDPipe } from '@nestjs/common';
import { AnimalsService } from './animals.service';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { Animal } from './animal.entity';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';

@ApiTags('Animais')
@Controller('animals')
export class AnimalsController {
    constructor(private animalsService: AnimalsService) { }

    @ApiOperation({ summary: 'Faz o cadastro de um novo animal no banco de dados.' })
    @ApiResponse({ status: 201, description: 'Retorna o novo animal cadastrado' })
    @Post()
    @UsePipes(ValidationPipe)
    createAnimal(@Body() createAnimalDto: CreateAnimalDto): Promise<Animal> {
        return this.animalsService.createAnimal(createAnimalDto);
    }

    @ApiOperation({ summary: 'Retorna a lista com todos os animais cadastrados pelo usuário com dado id.' })
    @ApiParam({ name: 'userId', description: 'id do usuário.' })
    @ApiResponse({ status: 200, description: 'Retorna a lista de animais de dado usuário' })
    @Get('user/:userId')
    getAnimals(@Param('userId', new ParseUUIDPipe()) userId: string): Promise<Animal[]> {
        return this.animalsService.getAnimals(userId);
    }

    @ApiOperation({ summary: 'Retorna o animal com dado id.' })
    @ApiParam({ name: 'id', description: 'id do animal.' })
    @ApiResponse({ status: 200, description: 'Retorna o animal que possui dado id.' })
    @Get(':id')
    getAnimalById(@Param('id', new ParseUUIDPipe()) id: string): Promise<Animal> {
        return this.animalsService.getAnimalById(id);
    }

    @ApiOperation({ summary: 'Exclui um animal do banco de dados.' })
    @ApiParam({ name: 'id', description: 'id do animal.' })
    @ApiResponse({ status: 200, description: 'O animal com dado id foi excluido.' })
    @Delete(':id')
    deleteAnimal(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
        return this.animalsService.deleteAnimal(id);
    }
}
