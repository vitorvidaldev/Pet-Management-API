import { Test, TestingModule } from "@nestjs/testing";
import { VaccinesController } from "./vaccines.controller";
import { VaccinesService } from "./vaccines.service";

describe("Vaccines Controller", () => {
  let vaccinesController: VaccinesController;
  let vaccinesService: VaccinesService;

  // beforeEach(async () => {
  //   const module: TestingModule = await Test.createTestingModule({
  //     controllers: [VaccinesController],
  //     providers: [VaccinesService],
  //   }).compile();

  //   vaccinesController = module.get<VaccinesController>(VaccinesController);
  //   vaccinesService = module.get<VaccinesService>(VaccinesService);
  // });

  // it("should be defined", () => {
  //   expect(controller).toBeDefined();
  // });

  // describe("Get vaccines", () => {
  //   it("should return an array of vaccines", async () => {});
  // });
});
