import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { Animal } from './animal.entity';
import { Repository } from 'typeorm';
import { UsersService } from 'src/users/users.service';

@Injectable()
export class AnimalsService {

    constructor(
        @InjectRepository(Animal)
        private animalRepository: Repository<Animal>,
        private userService: UsersService,
    ) { }

    async createAnimal(createAnimalDto: CreateAnimalDto): Promise<Animal> {
        const { name, birthDate, species, breed } = createAnimalDto;

        const animal = new Animal();
        animal.name = name;
        animal.birthDate = birthDate;
        animal.species = species.toLowerCase();
        animal.breed = breed.toLowerCase();
        animal.user = await this.userService.getUserById(createAnimalDto.userId);

        return await this.animalRepository.create(animal).save();
    }

    async getAnimals(): Promise<Animal[]> {
        return await this.animalRepository.createQueryBuilder('animal')
            .leftJoinAndSelect('animal.user', 'user')
            .getMany();
    }

    async getUserAnimals(userId: string): Promise<Animal[]> {
        return await this.animalRepository.createQueryBuilder('animal')
            .leftJoinAndSelect('animal.user', 'user')
            .where('animal.user = :userId', { userId: userId })
            .getMany();
    }

    async getAnimalById(id: string): Promise<Animal> {
        const animal = await this.animalRepository.findOne(id);
        if (!animal) throw new NotFoundException(`O animal com id ${id} não foi encontrado`);
        return animal;
    }

    async deleteAnimal(id: string): Promise<void> {
        await this.animalRepository.delete(id);
    }
}