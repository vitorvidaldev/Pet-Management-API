import { Controller, Post, UsePipes, ValidationPipe, Body, Get, Param, Delete, ParseUUIDPipe } from '@nestjs/common';
import { NotesService } from './note.service';
import { CreateNoteDto } from './dto/create-note.dto';
import { Note } from './note.entity';
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger';

@ApiTags('Notificações')
@Controller('notes')
export class NotesController {

    constructor(private notificationsService: NotesService) { }

    @ApiOperation({ summary: 'Cria nova notificação para o animal que possui dado id.' })
    @ApiResponse({ status: 201, description: 'Cria uma nova notificação para o animal com dado id.' })
    @Post()
    @UsePipes(ValidationPipe)
    createNotification(@Body() createNotificationDto: CreateNoteDto): Promise<Note> {
        return this.notificationsService.createNotification(createNotificationDto);
    }

    @Get('animal/:animalId')
    getNotifications(@Param('animalId', new ParseUUIDPipe()) id: string): Promise<Note[]> {
        return this.notificationsService.getNotifications(id);
    }

    @Get(':id')
    getNotificationById(@Param('id', new ParseUUIDPipe()) id: string): Promise<Note> {
        return this.notificationsService.getNotificationById(id);
    }

    @Delete(':id')
    deleteNotification(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
        return this.notificationsService.deleteNotification(id);
    }
}