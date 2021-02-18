import { CreateUserDto } from "src/users/dto/create-user.dto";
import { User } from "src/users/entity/user.entity";
import { Repository } from "typeorm";
import { v4 as uuidv4 } from "uuid";

export type MockType<T> = {
  [P in keyof T]: jest.Mock<{}>;
};

it("just passes", () => {
  expect(true).toBe(true);
});

export const mockedUser: Partial<User> = {
  id: uuidv4(),
  email: "yasha@gmail.com",
  password: "$2b$10$6XPgPUAm3oa83UzqpHfv4O2zsh7sdOD6j6bsnJl0NsowsLSRLLJv6",
  signature: "$2b$10$6XPgPUAm3oa83UzqpHfv4O",
  createDate: "2021-02-18 13:36:40.278347",
  active: true,
};

export const mockedUserDto: CreateUserDto = {
  email: "yasha@gmail.com",
  password: "Teste123...",
};

// @ts-ignore
export const repositoryMockFactory: () => MockType<Repository<any>> = jest.fn(
  () => ({
    remove: jest.fn((entity) => entity),
    delete: jest.fn((entity) => entity),
    findOne: jest.fn((entity) => entity),
    findOneOrFail: jest.fn(() => mockedUser),
    save: jest.fn((entity) => entity),
    create: jest.fn((entity) => entity),
    createImage: jest.fn((entity) => entity),
    preload: jest.fn((entity) => entity),
    findAndCount: jest.fn((entity) => entity),
    find: jest.fn((entity) => entity),
    findByIds: jest.fn((entity) => entity),
    count: jest.fn((entity) => entity),
  })
);
