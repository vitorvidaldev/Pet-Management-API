import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { typeOrmConfig } from "./config/typeorm.config";
import { UsersModule } from "./users/users.module";
import { AnimalsModule } from "./animals/animals.module";
import { VaccinesModule } from "./vaccines/vaccines.module";
import { NotesModule } from "./notes/notes.module";

@Module({
  imports: [
    TypeOrmModule.forRoot(typeOrmConfig),
    UsersModule,
    AnimalsModule,
    VaccinesModule,
    NotesModule
  ]
})
export class AppModule {}
