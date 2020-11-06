import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, OneToMany, Unique } from "typeorm";
import * as bcrypt from 'bcrypt';
import { Animal } from "src/animals/animal.entity";

@Entity('usuario')
@Unique(['email'])
export class User extends BaseEntity {
    @PrimaryGeneratedColumn('uuid', { name: 'id_usuario' })
    id: string;

    @Column()
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