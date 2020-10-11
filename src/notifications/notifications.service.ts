import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateNotificationDto } from './dto/create-notification.dto';
import { Notification } from './notification.entity';
import { Repository } from 'typeorm';
import { AnimalsService } from 'src/animals/animals.service';

@Injectable()
export class NotificationsService {

    constructor(
        @InjectRepository(Notification)
        private notificationRepository: Repository<Notification>,
        private animalService: AnimalsService,
    ) { }

    async getNotifications(animalId: string): Promise<Notification[]> {
        const query = this.notificationRepository.createQueryBuilder('notification').where('notification.animal = :animaId', { animalId: animalId });

        const notifications = await query.getMany();
        return notifications;
    }

    async getNotificationById(id: string): Promise<Notification> {
        const found = await this.notificationRepository.findOne(id);

        if (!found) {
            throw new NotFoundException('Notificação com ID ' + id + ' não foi encontrada');
        }

        return found;
    }

    async createNotification(createNotificationDto: CreateNotificationDto): Promise<Notification> {
        const { title, description, notificationDate, frequency, animalId } = createNotificationDto;

        const animal = await this.animalService.getAnimalById(animalId);

        const notification = new Notification();
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