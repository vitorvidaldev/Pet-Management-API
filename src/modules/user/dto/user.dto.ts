import { IsBoolean, IsNotEmpty, IsString } from 'class-validator';
import { Pet } from 'src/modules/pet/pet.entity';
import { User } from '../user.entity';

export class UserDTO {
  @IsString()
  @IsNotEmpty()
  id: string;

  @IsString()
  @IsNotEmpty()
  email: string;

  @IsBoolean()
  @IsNotEmpty()
  isActive: boolean;

  @IsString()
  @IsNotEmpty()
  creationDate: string;

  @IsNotEmpty()
  pets: Pet[];

  toUser(): User {
    const user = new User();
    user.id = this.id;
    user.email = this.email;
    user.isActive = this.isActive;
    user.creationDate = this.creationDate;
    user.pets = this.pets;
    return user;
  }
}
