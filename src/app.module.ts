import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { typeOrmConfig } from './config/typeorm.config';
import { PetModule } from './modules/pet/pet.module';
import { NoteModule } from './modules/note/note.module';
import { UserModule } from './modules/user/user.module';
import { VaccineModule } from './modules/vaccine/vaccine.module';

@Module({
  imports: [
    TypeOrmModule.forRoot(typeOrmConfig),
    UserModule,
    PetModule,
    VaccineModule,
    NoteModule,
  ],
})
export class AppModule {}
