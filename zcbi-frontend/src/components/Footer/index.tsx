import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
import React from 'react';
const Footer: React.FC = () => {
  const defaultMessage = '纯动力出品 chiwen.com';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: '纯动力智能 BI',
          title: '纯动力智能 BI',
          href: 'https://github.com/ZzCcwing/',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/ZzCcwing/',
          blankTarget: true,
        },
        {
          key: '纯动力智能 BI',
          title: '纯动力智能 BI',
          href: 'https://github.com/ZzCcwing/',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
