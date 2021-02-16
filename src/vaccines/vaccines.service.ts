import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { CreateVaccineDto } from "./dto/create-vaccine.dto";
import { GetVaccinesDto } from "./dto/get-vaccines.dto";
import { Vaccine } from "./vaccine.entity";

@Injectable()
export class VaccinesService {
  constructor(
    @InjectRepository(Vaccine)
    private vaccineRepository: Repository<Vaccine>
  ) {}

  async getVaccines(filterDto: GetVaccinesDto): Promise<Vaccine[]> {
    const query = this.vaccineRepository.createQueryBuilder("vaccines");

    if (filterDto.species) {
      query.andWhere("vaccines.species LIKE :species", {
        species: filterDto.species,
      });
    }

    if (filterDto.breed) {
      query.andWhere("vaccines.species LIKE :species", {
        species: filterDto.species,
      });
    }

    return await query.getMany();
  }

  async createVaccine(createVaccineDto: CreateVaccineDto): Promise<Vaccine> {
    return await this.vaccineRepository
      .create({
        name: createVaccineDto.name,
        species: createVaccineDto.species,
        breed: createVaccineDto.breed,
        monthsAfterBirth: createVaccineDto.monthsAfterBirth,
      })
      .save();
  }
}
