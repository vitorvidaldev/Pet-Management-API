import { JwtModule } from "@nestjs/jwt";
import { Test, TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import {
  mockedUser,
  mockedUserDto,
  MockType,
  repositoryMockFactory
} from "src/mock/user";
import { Repository } from "typeorm";
import { User } from "../entity/user.entity";
import { UsersService } from "../users.service";

describe("UsersService", () => {
  let service: UsersService;
  let repositoryMock: MockType<Repository<User>>;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
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
    repositoryMock = module.get(getRepositoryToken(User));
  });

  it("Service should be defined", () => {
    expect(service).toBeDefined();
  });

  describe("Create a user", () => {
    it("should create a user", async () => {
      repositoryMock.create.mockReturnValue({ save: () => mockedUser });

      repositoryMock.findOne.mockReturnValue(undefined);
      expect(await service.createUser(mockedUserDto)).toEqual(mockedUser);
      expect(repositoryMock.create).toHaveBeenCalledWith(mockedUser);
    });
  });
});
