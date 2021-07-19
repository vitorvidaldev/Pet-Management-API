import { Module } from "@nestjs/common";
import { PetController } from "./pet.controller";
import { PetService } from "./pet.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Pet } from "./pet.entity";
import { UsersModule } from "../user/user.module";

@Module({
  imports: [TypeOrmModule.forFeature([Pet]), UsersModule],
  controllers: [PetController],
  providers: [PetService],
  exports: [PetService]
})
export class PetModule {}
