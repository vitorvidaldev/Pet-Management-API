import { Module } from '@nestjs/common';
import { NotificationsController } from './notifications.controller';
import { NotificationsService } from './notifications.service';
import { AnimalsModule } from 'src/animals/animals.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Notification } from './notification.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Notification]),
    AnimalsModule,
  ],
  controllers: [NotificationsController],
  providers: [NotificationsService]
})
export class NotificationsModule { }
