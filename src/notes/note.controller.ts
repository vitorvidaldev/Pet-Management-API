import { Controller, Post, UsePipes, ValidationPipe, Body, Get, Param, Delete, ParseUUIDPipe } from '@nestjs/common';
import { NotesService } from './note.service';
import { CreateNoteDto } from './dto/create-note.dto';
import { Note } from './note.entity';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';

@ApiTags('Notas')
@Controller('notes')
export class NotesController {
    constructor(private notesService: NotesService) { }

    @ApiOperation({ summary: 'Cria nova nota para o animal que possui dado id.' })
    @ApiResponse({ status: 201, description: 'Cria uma nova nota para o animal com dado id.' })
    @Post()
    @UsePipes(ValidationPipe)
    createNote(@Body() createnoteDto: CreateNoteDto): Promise<Note> {
        return this.notesService.createNote(createnoteDto);
    }

    @ApiOperation({ summary: 'Retorna todas as nota de um dado animal' })
    @ApiParam({ name: 'animalId', description: 'id de um animal' })
    @ApiResponse({ status: 200, description: 'Retorna uma lista de notas' })
    @Get('animal/:animalId')
    getNotes(@Param('animalId', new ParseUUIDPipe()) id: string): Promise<Note[]> {
        return this.notesService.getNotes(id);
    }

    @ApiOperation({ summary: 'Retorna uma nota que possui dado id' })
    @ApiParam({ name: 'id', description: 'id de uma nota' })
    @ApiResponse({ status: 200, description: 'Retorna uma nota' })
    @Get(':id')
    getNoteById(@Param('id', new ParseUUIDPipe()) id: string): Promise<Note> {
        return this.notesService.getNoteById(id);
    }

    @ApiOperation({ summary: 'Exclui uma nota que possui dado id' })
    @ApiParam({ name: 'id', description: 'id de uma nota' })
    @ApiResponse({ status: 200, description: 'Retorna void' })
    @Delete(':id')
    deleteNote(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
        return this.notesService.deleteNote(id);
    }
}