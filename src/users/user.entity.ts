import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm";
import * as bcrypt from 'bcrypt';
import { Animal } from "src/animals/animal.entity";

@Entity('usuario')
export class User extends BaseEntity {
    @PrimaryGeneratedColumn('uuid', { name: 'id_usuario' })
    id: string;

    // O sistema só permite o cadastro de uma conta por email
    @Column({ name: 'email', length: 40, unique: true })
    email: string;

    @Column({ name: 'senha', length: 200 })
    password: string;

    @Column({ name: 'assinatura' })
    signature: string;

    @OneToMany(() => Animal, animal => animal.user)
    animals: Animal[];

    async validatePassword(password: string): Promise<boolean> {
        const hash = await bcrypt.hash(password, this.signature);
        return hash === this.password;
    }
}