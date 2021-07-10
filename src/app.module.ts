import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { typeOrmConfig } from "./config/typeorm.config";
import { PetModule } from "./modules/pet/pet.module";
import { NoteModule } from "./modules/note/note.module";
import { UsersModule } from "./modules/user/users.module";
import { VaccinesModule } from "./modules/vaccine/vaccines.module";

@Module({
  imports: [
    TypeOrmModule.forRoot(typeOrmConfig),
    UsersModule,
    PetModule,
    VaccinesModule,
    NoteModule
  ]
})
export class AppModule {}
