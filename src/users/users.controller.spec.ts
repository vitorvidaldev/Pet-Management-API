import { Test, TestingModule } from "@nestjs/testing";
import { CreateUserDto } from "./dto/create-user.dto";
import { User } from "./user.entity";
import { UsersController } from "./users.controller";
import { UsersService } from "./users.service";

describe("Users Controller", () => {
  let usersController: UsersController;
  let usersService: UsersService;

  const testNewUser: CreateUserDto = {
    email: "teste@gmail.com",
    password: "Teste123...",
  };

  const testUser: Partial<User> = {
    email: "teste@gmail.com",
    password: "Teste123...",
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [UsersController],
      providers: [
        {
          provide: UsersService,
          useValue: {
            createUser: jest.fn().mockReturnValue([testUser]),
          },
        },
      ],
    }).compile();

    usersService = module.get<UsersService>(UsersService);
    usersController = module.get<UsersController>(UsersController);
  });

  it("Controller should be defined", () => {
    expect(usersController).toBeDefined();
  });

  it("Service should be defined", () => {
    expect(usersService).toBeDefined();
  });

  describe("Create new account", () => {
    it("Should create a new user", async () => {
      await expect(usersController.createUser(testNewUser)).resolves.toEqual({
        id: "a uuid",
        ...testNewUser,
      });
    });
  });
});
