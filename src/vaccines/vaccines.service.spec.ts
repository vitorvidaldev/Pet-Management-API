import { Test, TestingModule } from "@nestjs/testing";
import { VaccinesService } from "./vaccines.service";

describe("VaccinesService", () => {
  let service: VaccinesService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [VaccinesService],
    }).compile();

    service = module.get<VaccinesService>(VaccinesService);
  });

  it("should be defined", () => {
    expect(service).toBeDefined();
  });
});
