import { Body, Controller, Post, ValidationPipe } from '@nestjs/common';
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger';
import { CreateVaccineDto } from './dto/create-vaccine.dto';
import { Vaccine } from './vaccine.entity';
import { VaccinesService } from './vaccines.service';

@ApiTags('Vacinas')
@Controller('vaccines')
export class VaccinesController {

    constructor(private vaccinesService: VaccinesService) { }

    @ApiOperation({ summary: 'Cadastro de novas vacinas no banco de dados' })
    @ApiResponse({ status: 201, description: 'Vacina cadastrada com sucesso.' })
    @Post()
    createVaccine(@Body(ValidationPipe) createVaccineDto: CreateVaccineDto): Promise<Vaccine> {
        return this.vaccinesService.createVaccine(createVaccineDto);
    }
}
