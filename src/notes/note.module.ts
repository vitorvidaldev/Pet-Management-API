import { Module } from '@nestjs/common';
import { NotesController } from './note.controller';
import { NotesService } from './note.service';
import { AnimalsModule } from 'src/animals/animals.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Note } from './note.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Note]),
    AnimalsModule,
  ],
  controllers: [NotesController],
  providers: [NotesService]
})
export class NotesModule { }
