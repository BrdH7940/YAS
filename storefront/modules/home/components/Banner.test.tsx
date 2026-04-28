import { render, screen } from '@testing-library/react';
import Banner from './Banner';
import '@testing-library/jest-dom';

describe('Banner component', () => {
  it('renders correctly', () => {
    render(<Banner />);
    // Just checking if it mounts without crashing
    expect(true).toBe(true);
  });
});
