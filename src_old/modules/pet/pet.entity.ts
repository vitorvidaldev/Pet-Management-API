import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  OneToMany,
  JoinColumn,
  CreateDateColumn,
} from 'typeorm';
import { Note } from '../note/note.entity';
import { User } from '../user/user.entity';
import { Vaccine } from '../vaccine/vaccine.entity';

@Entity('pet')
export class Pet extends BaseEntity {
  @PrimaryGeneratedColumn('uuid', { name: 'id' })
  id: string;

  @Column({ name: 'name' })
  name: string;

  @Column({ name: 'birthdate', type: 'timestamp' })
  birthDate: string;

  @Column({ name: 'species' })
  species: string;

  @Column({ name: 'breed' })
  breed: string;

  @CreateDateColumn({
    type: 'timestamp',
    name: 'creation_date',
  })
  creationDate: string;

  @ManyToOne(() => User, (user) => user.pets, { onDelete: 'CASCADE' })
  @JoinColumn({ name: 'user_id' })
  user: User;

  @OneToMany(() => Vaccine, (vaccine) => vaccine.pet)
  vaccines: Vaccine[];

  @OneToMany(() => Note, (note) => note.pet)
  notes: Note[];
}
