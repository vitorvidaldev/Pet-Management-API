import {
  Body,
  Controller,
  Get,
  Post,
  UseGuards,
  ValidationPipe,
} from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import {
  ApiBearerAuth,
  ApiOperation,
  ApiResponse,
  ApiTags,
} from '@nestjs/swagger';
import { CreateVaccineDto } from './dto/create-vaccine.dto';
import { VaccineService } from './vaccine.service';

@ApiTags('Vaccine')
@ApiResponse({ status: 401, description: 'Unauthorized' })
@Controller('vaccines')
@UseGuards(AuthGuard())
export class VaccinesController {
  constructor(private vaccinesService: VaccineService) {}

  // TODO: Add filter parameter
  @ApiOperation({ summary: 'Returns vaccines available' })
  @ApiResponse({
    status: 200,
    description: 'Lists all indexed vaccines',
  })
  @Get()
  @ApiBearerAuth('jwt')
  getVaccines(): void {
    // return this.vaccinesService.getVaccines();
  }

  @ApiOperation({ summary: 'Registration of new vaccines in the database' })
  @ApiResponse({ status: 201, description: 'Vaccine successfully registered.' })
  @Post()
  @ApiBearerAuth('jwt')
  createVaccine(
    @Body(ValidationPipe) createVaccineDto: CreateVaccineDto,
  ): void {
    // return this.vaccinesService.createVaccine(createVaccineDto);
  }
}
