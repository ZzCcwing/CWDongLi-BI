import {Card, Col, Divider, Row, Typography} from 'antd';
import React from 'react';

/**
 * 每个单独的卡片，为了复用样式抽成了组件
 * @param param0
 * @returns
 */
const InfoCard: React.FC<{
  title: string;
  desc: string;
}> = ({ title, desc }) => {
  return (
    // eslint-disable-next-line react/jsx-no-undef
    <Card title={title}>
      <p>{desc}</p>
    </Card>
  );
};

const Welcome: React.FC = () => {

  const features = [
    {
      title: '数据可视化',
      desc: '通过图表、图形等形式将数据转化为直观的可视化展示，帮助用户更好地理解数据。',
    },
    {
      title: '报表生成',
      desc: '支持定制化报表的生成，用户可以根据需要选择数据源、指标等来生成报表。',
    },
    {
      title: '智能分析',
      desc: '利用AI和机器学习技术，对数据进行深入分析，提供智能化的洞察和建议。',
    },
    // ... 可以继续添加更多功能卡片
  ];

  return (
    <div style={{ padding: '24px' }}>
      <Typography.Title level={2}>智能BI系统功能介绍</Typography.Title>
      {/* eslint-disable-next-line react/jsx-no-undef */}
      <Divider />
      <Row gutter={[16, 16]}>
        {features.map((feature, index) => (
          <Col key={index} xs={24} sm={12} md={8} lg={6}>
            <InfoCard title={feature.title} desc={feature.desc} />
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default Welcome;
