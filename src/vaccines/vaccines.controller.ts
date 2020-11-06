import { Body, Controller, Get, Post, Query, UseGuards, ValidationPipe } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger';
import { CreateVaccineDto } from './dto/create-vaccine.dto';
import { GetVaccinesDto } from './dto/get-vaccines.dto';
import { Vaccine } from './vaccine.entity';
import { VaccinesService } from './vaccines.service';

@ApiTags('Vacinas')
@UseGuards(AuthGuard())
@ApiResponse({ status: 401, description: 'Usuário não autorizado' })
@Controller('vaccines')
export class VaccinesController {

    constructor(private vaccinesService: VaccinesService) { }

    @ApiOperation({ summary: 'Retorna as vacinas cadastradas' })
    @ApiResponse({ status: 200, description: 'Lista de vacinas cadastradas, de acordo com os parâmentros' })
    @Get()
    getVaccines(@Query(ValidationPipe) filterDto: GetVaccinesDto): Promise<Vaccine[]> {
        return this.vaccinesService.getVaccines(filterDto);
    }

    @ApiOperation({ summary: 'Cadastro de novas vacinas no banco de dados' })
    @ApiResponse({ status: 201, description: 'Vacina cadastrada com sucesso.' })
    @Post()
    createVaccine(@Body(ValidationPipe) createVaccineDto: CreateVaccineDto): Promise<Vaccine> {
        return this.vaccinesService.createVaccine(createVaccineDto);
    }
}
