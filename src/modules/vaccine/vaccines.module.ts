import { Module } from "@nestjs/common";
import { VaccinesController } from "./vaccines.controller";
import { VaccineService } from "./vaccine.service";
import { AnimalsModule } from "src/animals/animals.module";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Vaccine } from "./vaccine.entity";
import { UsersModule } from "src/users/users.module";

@Module({
  imports: [TypeOrmModule.forFeature([Vaccine]), AnimalsModule, UsersModule],
  controllers: [VaccinesController],
  providers: [VaccineService],
  exports: [VaccineService]
})
export class VaccinesModule {}
