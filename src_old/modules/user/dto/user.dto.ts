import { IsBoolean, IsNotEmpty, IsString } from 'class-validator';
import { Pet } from 'src_old/modules/pet/pet.entity';
import { User } from '../user.entity';

export class UserDTO {
  @IsString()
  @IsNotEmpty()
  id: string;

  @IsString()
  @IsNotEmpty()
  email: string; // TODO: Encrypt email

  @IsBoolean()
  @IsNotEmpty()
  isActive: boolean; // TODO: Vefify if user is active

  @IsString()
  @IsNotEmpty()
  creationDate: string; // TODO: Filter by creation date

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
