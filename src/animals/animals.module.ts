import { Module } from "@nestjs/common";
import { AnimalsController } from "./animals.controller";
import { AnimalsService } from "./animals.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import { UsersModule } from "src/users/users.module";
import { Animal } from "./animal.entity";

@Module({
  imports: [TypeOrmModule.forFeature([Animal]), UsersModule],
  controllers: [AnimalsController],
  providers: [AnimalsService],
  exports: [AnimalsService],
})
export class AnimalsModule {}
