import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateVaccineDto } from './dto/create-vaccine.dto';
import { Vaccine } from './vaccine.entity';

@Injectable()
export class VaccineService {
  constructor(
    @InjectRepository(Vaccine)
    private repository: Repository<Vaccine>,
  ) {}

  // TODO: Add filter to the request
  async findAll(): Promise<Vaccine[]> {
    const query = this.repository.createQueryBuilder('vaccine');

    return query.getMany();
  }

  async save(dto: CreateVaccineDto): Promise<Vaccine> {
    return this.repository
      .create({
        name: dto.name,
        species: dto.species,
        breed: dto.breed,
        monthsAfterBirth: dto.monthsAfterBirth,
      })
      .save();
  }
}
