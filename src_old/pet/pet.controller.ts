import {
  Controller,
  Post,
  Body,
  ValidationPipe,
  UsePipes,
  Get,
  Param,
  Delete,
  ParseUUIDPipe,
  UseGuards,
} from '@nestjs/common';
import { PetService } from './pet.service';
import { CreatePetDto } from './dto/create-pet.dto';
import { Pet } from './pet.entity';
import {
  ApiBearerAuth,
  ApiOperation,
  ApiParam,
  ApiResponse,
  ApiTags,
} from '@nestjs/swagger';
import { AuthGuard } from '@nestjs/passport';

@ApiTags('Pet')
@ApiResponse({ status: 401, description: 'Unauthorized' })
@Controller('pet')
@UseGuards(AuthGuard())
export class PetController {
  constructor(private petService: PetService) {}

  @ApiOperation({
    summary: 'Register a new pet in the database.',
  })
  @ApiResponse({ status: 201, description: 'Returns the saved pet.' })
  @Post()
  @ApiBearerAuth('jwt')
  @UsePipes(ValidationPipe)
  save(@Body() petDto: CreatePetDto): Promise<Pet> {
    return this.petService.save(petDto);
  }

  @ApiOperation({ summary: 'Returns the list with all saved pets.' })
  @ApiResponse({ status: 200, description: 'Returns all pets.' })
  @Get()
  @ApiBearerAuth('jwt')
  findAll(): Promise<Pet[]> {
    return this.petService.findAll();
  }

  @ApiOperation({
    summary: 'Returns the list with the user pets.',
  })
  @ApiResponse({
    status: 200,
    description: 'Returns the list of user pets.',
  })
  @Get('/user/:id')
  @ApiBearerAuth('jwt')
  findByUser(@Param('id', new ParseUUIDPipe()) id: string): Promise<Pet[]> {
    return this.petService.findByUser(id);
  }

  @ApiOperation({ summary: 'Returns pet with the given id.' })
  @ApiParam({ name: 'id', description: 'id.' })
  @ApiResponse({
    status: 200,
    description: 'Returns pet with the given id.',
  })
  @Get(':id')
  @ApiBearerAuth('jwt')
  findById(@Param('id', new ParseUUIDPipe()) id: string): Promise<Pet> {
    return this.petService.findById(id);
  }

  @ApiOperation({ summary: 'Deletes a pet from the database.' })
  @ApiParam({ name: 'id', description: 'id.' })
  @ApiResponse({
    status: 200,
    description: 'The pet with the given id was excluded.',
  })
  @Delete(':id')
  @ApiBearerAuth('jwt')
  deleteById(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
    return this.petService.deleteById(id);
  }
}
