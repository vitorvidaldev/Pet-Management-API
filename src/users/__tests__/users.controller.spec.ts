import { JwtModule } from "@nestjs/jwt";
import { Test, TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { mockedUser, repositoryMockFactory } from "src/mock/user";
import { User } from "../entity/user.entity";
import { JwtStrategy } from "../jwt.strategy";
import { UsersController } from "../users.controller";
import { UsersService } from "../users.service";

describe("Users Controller", () => {
  let controller: UsersController;
  let service: UsersService;

  beforeEach(async () => {
    jest.resetModules();
    const module: TestingModule = await Test.createTestingModule({
      controllers: [UsersController],
      providers: [
        JwtStrategy,
        UsersService,
        {
          provide: getRepositoryToken(User),
          useFactory: repositoryMockFactory
        }
      ],
      imports: [
        JwtModule.register({
          secret: "topSecret51",
          signOptions: {
            expiresIn: 3600
          }
        })
      ]
    }).compile();

    service = module.get<UsersService>(UsersService);
    controller = module.get<UsersController>(UsersController);
  });

  it("Controller should be defined", () => {
    expect(controller).toBeDefined();
  });

  it("Service should be defined", () => {
    expect(service).toBeDefined();
  });

  describe("create User", () => {
    it("should return userId on create", async () => {
      jest
        .spyOn(service, "createUser")
        .mockImplementation(() => Promise.resolve(mockedUser));

      expect(await controller.createUser(mockedUser as User)).toBe(mockedUser);
    });
  });
});
