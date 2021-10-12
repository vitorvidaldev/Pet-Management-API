import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CreatePetDto } from './dto/create-pet.dto';
import { Pet } from './pet.entity';
import { Repository } from 'typeorm';
import { UserService } from '../user/user.service';

@Injectable()
export class PetService {
  constructor(
    @InjectRepository(Pet)
    private petRepository: Repository<Pet>,
    private userService: UserService,
  ) {}

  async save(petDto: CreatePetDto): Promise<Pet> {
    const { name, birthDate, species, breed } = petDto;

    const pet = new Pet();
    pet.name = name;
    pet.birthDate = birthDate;
    pet.species = species.toLowerCase();
    pet.breed = breed.toLowerCase();
    const userDto = await this.userService.findById(petDto.userId);
    pet.user = userDto.toUser();
    return this.petRepository.create(pet).save();
  }

  async findAll(): Promise<Pet[]> {
    return this.petRepository
      .createQueryBuilder('pet')
      .leftJoinAndSelect('pet.user', 'user')
      .getMany();
  }

  async findByUser(userId: string): Promise<Pet[]> {
    return this.petRepository
      .createQueryBuilder('pet')
      .leftJoinAndSelect('pet.user', 'user')
      .where('pet.user = :userId', { userId: userId })
      .getMany();
  }

  async findById(id: string): Promise<Pet> {
    const pet = await this.petRepository.findOne(id);
    if (!pet)
      throw new NotFoundException(`The pet with id ${id} was not found`);
    return pet;
  }

  async deleteById(id: string): Promise<void> {
    await this.petRepository.delete(id);
  }
}
