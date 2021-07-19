import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn
} from "typeorm";
import { Pet } from "../pet/pet.entity";

@Entity("vaccine")
export class Vaccine extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_vaccine" })
  id?: number;

  @Column({ name: "name" })
  name!: string;

  @Column({ name: "species" })
  species!: string;

  @Column({ name: "breed" })
  breed!: string;

  @Column({ name: "months_after_birth" })
  monthsAfterBirth!: string;

  @ManyToOne(
    () => Pet,
    pet => pet.vaccines,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "pet_id" })
  pet?: Pet;
}
