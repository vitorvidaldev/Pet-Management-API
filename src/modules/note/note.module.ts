import { Module } from "@nestjs/common";
import { NoteController } from "./note.controller";
import { NoteService } from "./note.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Note } from "./note.entity";
import { PetModule } from "../pet/pet.module";
import { UsersModule } from "../user/users.module";

@Module({
  imports: [TypeOrmModule.forFeature([Note]), PetModule, UsersModule],
  controllers: [NoteController],
  providers: [NoteService]
})
export class NoteModule {}
