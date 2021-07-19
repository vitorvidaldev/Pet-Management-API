import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  OneToMany,
  Unique,
  CreateDateColumn
} from "typeorm";
import * as bcrypt from "bcrypt";
import { Pet } from "src/modules/pet/pet.entity";

@Entity("User")
@Unique(["email"])
export class User extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_user" })
  id: string;

  @Column()
  email: string;

  @Column({ name: "password", length: 200 })
  password: string;

  @Column({ name: "signature" })
  signature: string;

  @CreateDateColumn({
    type: "timestamp",
    name: "create_date"
  })
  createDate: string;

  @Column({ type: "bool", name: "active", default: true })
  active: boolean;

  @OneToMany(
    () => Pet,
    pet => pet.user
  )
  pets: Pet[];

  async validatePassword(password: string): Promise<boolean> {
    const hash = await bcrypt.hash(password, this.signature);
    return hash === this.password;
  }
}
