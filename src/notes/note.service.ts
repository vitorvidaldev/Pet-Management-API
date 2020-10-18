import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateNoteDto } from './dto/create-note.dto';
import { Note } from './note.entity';
import { Repository } from 'typeorm';
import { AnimalsService } from 'src/animals/animals.service';

@Injectable()
export class NotesService {

    constructor(
        @InjectRepository(Note)
        private notificationRepository: Repository<Note>,
        private animalService: AnimalsService,
    ) { }

    async getNotifications(animalId: string): Promise<Note[]> {
        const query = this.notificationRepository.createQueryBuilder('notification').where('notification.animal = :animaId', { animalId: animalId });

        const notifications = await query.getMany();
        return notifications;
    }

    async getNotificationById(id: string): Promise<Note> {
        const found = await this.notificationRepository.findOne(id);

        if (!found) {
            throw new NotFoundException('Notificação com ID ' + id + ' não foi encontrada');
        }

        return found;
    }

    async createNotification(createNotificationDto: CreateNoteDto): Promise<Note> {
        const { title, description, notificationDate, frequency, animalId } = createNotificationDto;

        const animal = await this.animalService.getAnimalById(animalId);

        const notification = new Note();
        notification.title = title;
        notification.description = description;
        notification.notificationDate = notificationDate;
        notification.frequency = frequency;
        notification.animal = animal;

        return await this.notificationRepository.create(notification).save();
    }

    async deleteNotification(id: string): Promise<void> {
        const result = await this.notificationRepository.delete(id);
    }
}