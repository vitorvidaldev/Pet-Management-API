import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreateAnimalDto } from './dto/create-animal.dto';
import { Animal } from './animal.entity';
import { Repository } from 'typeorm';
import { User } from 'src/users/user.entity';

@Injectable()
export class AnimalsService {

    constructor(
        @InjectRepository(Animal)
        private animalRepository: Repository<Animal>,
    ) { }

    async createAnimal(createAnimalDto: CreateAnimalDto, user: User): Promise<Animal> {
        const { name, birthDate, species, breed } = createAnimalDto;

        const animal = new Animal();
        animal.name = name;
        animal.birthDate = birthDate;
        animal.species = species.toLowerCase();
        animal.breed = breed.toLowerCase();
        animal.user = user;

        return await this.animalRepository.create(animal).save();
    }

    async getAnimals(user: User): Promise<Animal[]> {
        return await this.animalRepository.createQueryBuilder('animal')
            .leftJoinAndSelect('animal.user', 'user')
            .where('animal.user = :user', { user: user })
            .getMany();
    }

    async getAnimalById(id: string, user: User): Promise<Animal> {
        const animal = await this.animalRepository.findOne({ where: { id: id, user: user } });
        if (!animal) throw new NotFoundException('O animal com id ' + id + ' não foi encontrado');
        return animal;
    }

    async deleteAnimal(id: string, user: User): Promise<void> {
        await this.animalRepository.delete({ id, user: user });
    }
}