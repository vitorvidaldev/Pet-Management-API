import { Module } from '@nestjs/common';
import { VaccinesController } from './vaccines.controller';
import { VaccinesService } from './vaccines.service';
import { AnimalsModule } from 'src/animals/animals.module';

@Module({
  imports: [
    AnimalsModule,
  ],
  controllers: [VaccinesController],
  providers: [VaccinesService]
})
export class VaccinesModule {}
