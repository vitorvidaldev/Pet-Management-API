import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  CreateDateColumn,
  JoinColumn
} from "typeorm";
import { Pet } from "../pet/pet.entity";

@Entity("note")
export class Note extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id" })
  id!: string;

  @Column({ name: "type" })
  type!: string;

  @Column({ name: "title" })
  title!: string;

  @Column({ name: "description" })
  description!: string;

  @CreateDateColumn({ name: "creation_date" })
  creationDate!: string;

  @Column({ name: "trigger_date" })
  triggerDate!: Date;

  @Column({ name: "frequency" })
  frequency!: string;

  @ManyToOne(
    () => Pet,
    pet => pet.notes,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "pet_id" })
  pet!: Pet;
}
