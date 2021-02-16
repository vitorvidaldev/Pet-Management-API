import { Module } from "@nestjs/common";
import { NotesController } from "./notes.controller";
import { NotesService } from "./notes.service";
import { AnimalsModule } from "src/animals/animals.module";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Note } from "./notes.entity";
import { UsersModule } from "src/users/users.module";

@Module({
  imports: [TypeOrmModule.forFeature([Note]), AnimalsModule, UsersModule],
  controllers: [NotesController],
  providers: [NotesService],
})
export class NotesModule {}
