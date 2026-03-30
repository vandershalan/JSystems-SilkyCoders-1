import '@testing-library/jest-dom'
import { server } from './mocks/server'
import { beforeAll, afterEach, afterAll } from 'vitest'

// Setup MSW server for all tests
beforeAll(() => server.listen())

afterEach(() => server.resetHandlers())

afterAll(() => server.close())

// Mock ResizeObserver for tests that use assistant-ui
globalThis.ResizeObserver = class ResizeObserver {
  observe() {
    // do nothing
  }
  unobserve() {
    // do nothing
  }
  disconnect() {
    // do nothing
  }
}
