import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm";
import * as bcrypt from 'bcrypt';
import { Animal } from "src/animals/animal.entity";
import { ApiProperty, ApiTags } from "@nestjs/swagger";

@ApiTags('Usuários')
@Entity('usuario')
export class User extends BaseEntity {
    @PrimaryGeneratedColumn('uuid', { name: 'id_usuario' })
    @ApiProperty({ description: 'id de identificação' })
    id: string;

    @Column({ name: 'email', length: 40 })
    @ApiProperty({ description: 'email do usuário' })
    email: string;

    @Column({ name: 'senha', length: 200 })
    @ApiProperty({ description: 'senha do usuário' })
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