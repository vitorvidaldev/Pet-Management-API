import { Module } from "@nestjs/common";
import { VaccinesController } from "./vaccines.controller";
import { VaccinesService } from "./vaccines.service";
import { AnimalsModule } from "src/animals/animals.module";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Vaccine } from "./vaccine.entity";
import { UsersModule } from "src/users/users.module";

@Module({
  imports: [TypeOrmModule.forFeature([Vaccine]), AnimalsModule, UsersModule],
  controllers: [VaccinesController],
  providers: [VaccinesService],
  exports: [VaccinesService],
})
export class VaccinesModule {}
