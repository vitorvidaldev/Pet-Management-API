import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateVaccineDto } from './dto/create-vaccine.dto';
import { Vaccine } from './vaccine.entity';

@Injectable()
export class VaccinesService {
    constructor(
        @InjectRepository(Vaccine)
        private vaccineRepository: Repository<Vaccine>,
    ) { }

    async createVaccine(createVaccineDto: CreateVaccineDto): Promise<Vaccine> {
        return await this.vaccineRepository.create({
            name: createVaccineDto.name,
            species: createVaccineDto.species,
            race: createVaccineDto.race,
            monthsAfterBirth: createVaccineDto.monthsAfterBirth,
        });
    }

}
