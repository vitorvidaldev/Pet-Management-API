import { Controller, Post, UsePipes, ValidationPipe, Body, Get, Param, Delete, ParseUUIDPipe } from '@nestjs/common';
import { NotificationsService } from './notifications.service';
import { CreateNotificationDto } from './dto/create-notification.dto';
import { Notification } from './notification.entity';
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger';

@ApiTags('Notificações')
@Controller('notifications')
export class NotificationsController {

    constructor(private notificationsService: NotificationsService) { }

    @ApiOperation({ summary: 'Cria nova notificação para o animal que possui dado id.' })
    @ApiResponse({ status: 201, description: 'Cria uma nova notificação para o animal com dado id.' })
    @Post()
    @UsePipes(ValidationPipe)
    createNotification(@Body() createNotificationDto: CreateNotificationDto): Promise<Notification> {
        return this.notificationsService.createNotification(createNotificationDto);
    }

    @Get('animal/:animalId')
    getNotifications(@Param('animalId', new ParseUUIDPipe()) id: string): Promise<Notification[]> {
        return this.notificationsService.getNotifications(id);
    }

    @Get(':id')
    getNotificationById(@Param('id', new ParseUUIDPipe()) id: string): Promise<Notification> {
        return this.notificationsService.getNotificationById(id);
    }

    @Delete(':id')
    deleteNotification(@Param('id', new ParseUUIDPipe()) id: string): Promise<void> {
        return this.notificationsService.deleteNotification(id);
    }
}