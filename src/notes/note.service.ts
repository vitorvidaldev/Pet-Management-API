import { Injectable, NotFoundException } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { CreateNoteDto } from "./dto/create-note.dto";
import { Note } from "./note.entity";
import { Repository } from "typeorm";
import { AnimalsService } from "src/animals/animals.service";

@Injectable()
export class NotesService {
  constructor(
    @InjectRepository(Note)
    private noteRepository: Repository<Note>,
    private animalService: AnimalsService
  ) {}

  async createNote(createnoteDto: CreateNoteDto): Promise<Note> {
    const {
      noteType,
      title,
      description,
      noteDate,
      frequency,
      animalId,
    } = createnoteDto;

    const animal = await this.animalService.getAnimalById(animalId);

    const note = new Note();
    note.noteType = noteType.toLowerCase();
    note.title = title;
    note.description = description;
    note.noteDate = noteDate;
    note.frequency = frequency;
    note.animal = animal;

    return await this.noteRepository.create(note).save();
  }

  async getNotes(animalId: string): Promise<Note[]> {
    return await this.noteRepository
      .createQueryBuilder("note")
      .leftJoinAndSelect("note.animal", "animal")
      .andWhere("note.animal = :animalId", {
        animalId: animalId,
      })
      .getMany();
  }

  async getNoteById(id: string): Promise<Note> {
    const found = await this.noteRepository.findOne(id);
    if (!found) {
      throw new NotFoundException(`Nota com id ${id} não foi encontrada`);
    }
    return found;
  }

  async deleteNote(id: string): Promise<void> {
    await this.noteRepository.delete(id);
  }
}
