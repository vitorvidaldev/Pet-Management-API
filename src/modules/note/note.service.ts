import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateNoteDto } from './dto/create-note.dto';
import { Note } from './note.entity';
import { Repository } from 'typeorm';
import { PetService } from '../pet/pet.service';

@Injectable()
export class NoteService {
  constructor(
    @InjectRepository(Note)
    private noteRepository: Repository<Note>,
    private petService: PetService,
  ) {}

  async save(noteDto: CreateNoteDto): Promise<Note> {
    const {
      type: noteType,
      title,
      description,
      noteDate,
      frequency,
      petId,
    } = noteDto;

    const pet = await this.petService.findById(petId);

    const note = new Note();
    note.type = noteType.toLowerCase();
    note.title = title;
    note.description = description;
    note.triggerDate = noteDate;
    note.frequency = frequency;
    note.pet = pet;

    return this.noteRepository.create(note).save();
  }

  async findAll(id: string): Promise<Note[]> {
    return this.noteRepository
      .createQueryBuilder('note')
      .leftJoinAndSelect('note.pet', 'pet')
      .andWhere('note.pet = :id', {
        id: id,
      })
      .getMany();
  }

  async findById(id: string): Promise<Note> {
    const found = await this.noteRepository.findOne(id);
    if (!found) {
      throw new NotFoundException(`Note with id ${id} not found`);
    }
    return found;
  }

  async deleteById(id: string): Promise<void> {
    await this.noteRepository.delete(id);
  }
}
